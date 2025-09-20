$ErrorActionPreference = 'Stop'

function Resolve-JavadocExe {
    $candidates = @()

    if ($env:JAVADOC) { $candidates += $env:JAVADOC }

    try {
        $whereOut = & where.exe javadoc 2>$null
        if ($LASTEXITCODE -eq 0 -and $whereOut) { $candidates += $whereOut }
    } catch {}

    foreach ($javaHome in @($env:JAVA_HOME, $env:JDK_HOME)) {
        if ($javaHome) {
            $bin = Join-Path $javaHome 'bin\javadoc.exe'
            $candidates += $bin
        }
    }

    $programFiles = @($env:ProgramFiles, ${env:ProgramFiles(x86)})
    $possibleRoots = @(
        'Java',
        'Eclipse Adoptium',
        'Microsoft',
        'Zulu',
        'BellSoft',
        'Amazon Corretto'
    )
    foreach ($pf in $programFiles) {
        foreach ($root in $possibleRoots) {
            $rootPath = Join-Path $pf $root
            if (Test-Path $rootPath) {
                $javas = Get-ChildItem -Path $rootPath -Directory -Force -ErrorAction SilentlyContinue | Where-Object {
                    $_.Name -match 'jdk' -or $_.Name -match 'java'
                }
                foreach ($j in $javas) {
                    $exe = Join-Path $j.FullName 'bin\javadoc.exe'
                    if (Test-Path $exe) { $candidates += $exe }
                }
            }
        }
    }

    $unique = $candidates | Where-Object { $_ } | Select-Object -Unique
    foreach ($p in $unique) { if (Test-Path $p) { return $p } }
    return $null
}

# Ensure output directory exists
New-Item -ItemType Directory -Force -Path "docs\api" | Out-Null

# Locate javadoc
$javadoc = Resolve-JavadocExe
if (-not $javadoc) {
    throw "javadoc.exe not found. Please install a JDK and ensure javadoc is on PATH or set JAVA_HOME. Download a JDK from https://adoptium.net/ or https://jdk.java.net/"
}
Write-Host "Using javadoc: $javadoc"

# Generate Javadoc (HTML)
& $javadoc -d "docs\api" `
  -sourcepath "src" `
  -subpackages animation:game:geometry:interfaces:sprites `
  -classpath ".;biuoop-1.4.jar" `
  -encoding UTF-8 -charset UTF-8 -docencoding UTF-8 `
  -quiet

# Verify generation
$generated = Get-ChildItem -Recurse "docs\api" -Filter *.html -ErrorAction SilentlyContinue
if (-not $generated) {
    throw "Javadoc generation failed or produced no HTML files. Verify source path 'src' and package names."
}

# Flatten HTML into a single TXT file
$out = "docs\SpaceInvaders-javadoc.txt"
if (Test-Path $out) { Remove-Item $out -Force }

$files = $generated | Sort-Object FullName
foreach ($f in $files) {
    $html = Get-Content $f.FullName -Raw
    # Strip script/style blocks
    $html = [System.Text.RegularExpressions.Regex]::Replace(
        $html,
        '<script.*?</script>|<style.*?</style>',
        '',
        [System.Text.RegularExpressions.RegexOptions]::Singleline -bor [System.Text.RegularExpressions.RegexOptions]::IgnoreCase
    )
    # Convert line-break like tags to newlines for readability
    $html = [System.Text.RegularExpressions.Regex]::Replace(
        $html,
        '<br\s*/?>',
        [Environment]::NewLine,
        [System.Text.RegularExpressions.RegexOptions]::IgnoreCase
    )
    $html = [System.Text.RegularExpressions.Regex]::Replace(
        $html,
        '</(p|h\d|li|tr|div)>',
        [Environment]::NewLine,
        [System.Text.RegularExpressions.RegexOptions]::IgnoreCase
    )
    # Drop remaining tags
    $text = [System.Text.RegularExpressions.Regex]::Replace($html, '<[^>]+>', '')
    # Decode HTML entities and collapse excessive whitespace
    $text = [System.Net.WebUtility]::HtmlDecode($text)
    $text = $text -replace '\s{2,}', ' ' -replace '(\r?\n)\s+', '$1'

    Add-Content -Path $out -Encoding UTF8 -Value ("===== {0} ====={1}{2}{1}" -f $f.Name, [Environment]::NewLine, $text)
}

Write-Host "Wrote $out"