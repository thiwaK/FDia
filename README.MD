  <div align="center">
  <h1>FDia Xposed</h1>
  <br>
  <p>
    <a href="https://github.com/thiwaK/FDia/releases/latest">
      <img src="https://img.shields.io/github/v/release/thiwaK/FDia?color=green&logoColor=green&label=Download&logo=android" alt="Latest Release">
    </a> <a href="https://github.com/thiwaK/FDia/releases">
      <img src="https://img.shields.io/endpoint?url=https://raw.githubusercontent.com/thiwaK/FDia/badges/downloads.json&logo=Bookmeter&label=Counts&logoColor=yellow&color=yellow" alt="Total Downloads">
    </a>
  </p>
</div>

**FDia Xposed module by thiwaK 🇱🇰**  

FDia is an LSPosed/Xposed module that patches app checks to improve mod/tool compatibility by disabling specific runtime detections.

>[!IMPORTANT]  
> - I am not responsible for any complications that may occur when using this module. Use at your own risk.
> - App and version support is not guaranteed. Always read release notes before updating/downloading.
> - Issues without logs or reproduction steps will be ignored.


## Downloads 
<p>Get FDia from the official sources below</p>

<ul>
  <li>
    <a href="https://github.com/thiwaK/FDia/releases"><strong>GitHub Releases</strong></a><br>
  </li>
  <li>
    <a href="https://github.com/Xposed-Modules-Repo/lk.thiwak.fdia/releases"><strong>Xposed Modules Repository</strong></a><br>
  </li>
</ul>

## Patches

### MyDialog

- Google Play Integrity Check
- Comprehensive Security Check
- App Signature Check
- Xposed Detection
- Root Management Apps Detection
- Advanced Root Detection
- Tamper Detection
- Runtime Checks
- Developer Options Detection
- Hook Detection
- Debugging Detection
- SSL Class Modification Detection
- SSL Pinning Bypass Detection
- AppsFlyer Analytics (Manifest)
- Facebook SDK (Manifest)
- Unwanted Permissions Removed (Manifest)

### WOW
- Status: Planned

### ViU+
- Status: Planned


## Installation
- Download the latest release (or a version compatible with your target app).
- Install the APK on your Android device.
- Activate the module in LSPosed (or your preferred Xposed manager).
- Force stop the target app and relaunch it, or reboot the device.

## Usage Notes
- Confirm the module is enabled in LSPosed (and set to run in the appropriate process. Sometimes app-specific activation may be required).
- If a patched app updates, you may need to re-check compatibility or update FDia.
- If something breaks, disable the module and reboot to revert behavior.

## Troubleshooting
- App still fails integrity checks
  - Ensure FDia is enabled for the app's process
  - Force stop relevant app/apps and open again
  - Reboot the device if still failing
- Module causing instability? 
  - Disable FDia in LSPosed/Xposed manager and you may need to reboot
  - Then open an issue with logs/screenshots and steps to reproduce
- Error loading page (Error Code : 3)
  - Disable your `SSL-Unpinner` and try again

## Contributions & Issues
- Report bugs or request patches via the [issues page](https://github.com/thiwaK/FDia/issues) of the repository.
- Contributions welcome — fork the repo, make changes, and submit a pull request.

## Changelog
- See Releases on the GitHub page for versioned changelogs and release notes.

## License
- See the repository for license details.
