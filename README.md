# lisp_detection

## Modes

Available modes are:

* (lateral) lisp detection based on [Munnich et al.](https://github.com/munnich/lateral-lisp): `lisp`
* basic speech detection via noise gate: `noisegate`

### Adding modes

Modes are audio processing scripts written in MATLAB.
To add a mode, first edit `callAnalyze` to include a block for the mode, which is identified via a string.
Inside this block, `maxIterations`, the maximum number of consecutive windows to be analyzed before the results are processed, must be defined.
Additionally, the notification message, `alarmText`, needs to be set.
Finally, `i_and_count`'s second entry must be changed via the `_mode_Analyze` function.
This entry is initially set to zero and, after the number of windows given by `maxIterations` is analyzed, this value is compared to whether it is above zero, in which case the `alarmText` is displayed and a notification sound is played.

Next, define the main audio processing function, `_mode_Analyze`, which must take an audio array and analysis parameters as input and output a number to be added to `i_and_count`'s second entry.

Now, the calibration process is set up.
Inside `callCalibrate`, include a block for the mode, again identified via a string.
This block simply needs to include a line to set the `params` variable by calling the calibration function.
The calibration function, `_mode_Calibrate`, needs to accept an `audioDeviceReader`.
This object is used to read the microphone and create an audio array.
To change the length of audio used to calibrate, change the `SamplesPerFrame` property of the `audioDeviceReader`.
The function should output the parameters used for analysis.

For simple examples for analysis and calibration functions, look at `noiseGateAnalyze.m` and `noiseGateCalibrate.m`.