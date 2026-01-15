<div align="center">
  <h1>FDia Xposed</h1>
  <br>
</div>

**FDia Xposed module by thiwaK.**  

FDia is an LSPosed/Xposed module that patches app checks to improve mod/tool compatibility by disabling specific runtime detections.

>[!IMPORTANT]  
> - I am not responsible for any complications that may occur when using this module.
> - Specific versions may or may not be supported — read the release notes before downloading.
> - If you encounter issues, please open an issue on the project repository.


## Patches

### MyDialog

- Google Play Integrity Check
- Comprehensive Security Check
- App Signature Check
- Patch Xposed Detection
- Root Management Apps Detection
- Advanced Root Detection
- Tamper Detection
- Runtime Checks
- Developer Options Detection
- Hook Detection
- Debugging Detection
- SSL Class Modification Detection
- SSL Pinning Bypass Detection



### WOW


### ViU+


## Downloads
- **Release build**: [Download](https://github.com/thiwaK/FDia/releases/latest)

## Installation
- Star the repository if you like the project.
- Download the latest release: [https://github.com/thiwaK/FDia/releases/latest](https://github.com/thiwaK/FDia/releases/latest)
- Install the downloaded APK on your Android device.
- Activate the module in LSPosed (or your preferred Xposed manager).
- Reboot the device.
- Launch the target app — patched checks should now be disabled.

## Usage Notes
- Confirm the module is enabled in LSPosed (and set to run in the appropriate process. Sometimes app-specific activation may be required).
- If a patched app updates, you may need to re-check compatibility or update FDia.
- If something breaks, disable the module and reboot to revert behavior.

## Troubleshooting
- App still detects instrumentation/root/debugging? 
  - Ensure FDia is enabled for that app's process and try rebooting again.
- Module causing instability? 
  - Disable FDia in LSPosed and reboot; then open an issue with logs and steps to reproduce.

## Contributions & Issues
- Report bugs or request patches via the [issues page](https://github.com/thiwaK/FDia/issues) of the repository.
- Contributions welcome — fork the repo, make changes, and submit a pull request.

## Changelog
- See Releases on the GitHub page for versioned changelogs and release notes.

## License
- See the repository for license details.
