import requests
import json
import os

REPOS = [
    "thiwaK/FDia",
    "Xposed-Modules-Repo/lk.thiwak.fdia"
]

headers = {
    "Accept": "application/vnd.github+json"
}

token = os.getenv("GITHUB_TOKEN")
if token:
    headers["Authorization"] = f"Bearer {token}"

total_downloads = 0

for repo in REPOS:
    url = f"https://api.github.com/repos/{repo}/releases"
    response = requests.get(url, headers=headers)
    response.raise_for_status()

    releases = response.json()
    for release in releases:
        for asset in release.get("assets", []):
            total_downloads += asset.get("download_count", 0)

badge = {
    "schemaVersion": 1,
    "label": "Total Downloads",
    "message": f"{total_downloads:,}",
    "color": "yellow"
}

with open("downloads.json", "w") as f:
    json.dump(badge, f, indent=2)

print(f"Total downloads: {total_downloads}")
