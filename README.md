# lisp_detection

## Modes

Available modes are:

* (lateral) lisp detection based on [Munnich et al.](https://github.com/munnich/lateral-lisp): `lisp`
* basic speech detection via noise gate: `noisegate`

### Adding modes

#### Analysis

Modes are audio processing scripts written in MATLAB.
To add a mode, e.g. `myMode`, first edit `callAnalyze` to include a block for the mode, which is identified via a string.
Inside this block, `maxIterations`, the maximum number of consecutive windows to be analyzed before the results are processed, must be defined.
Finally, `i_and_count`'s second entry must be changed via the `myModeAnalyze` function.
This entry is initially set to zero and, after the number of windows given by `maxIterations` is analyzed, this value is compared to whether it is above zero, in which case a notification sound is played and the script restarts.

How these parameters are passed is defined within the Java method that calls the function. TODO.
In the case of multiple parameters, these will be passed as vectors or matrices, depending on whether they are grouped. If necessary, you can parse these in this block or do so in `myModeAnalyze`.

The block in `callAnalyze` should look something like this:

```Matlab
elseif mode == "mymode"
    % define the maximum number of windows to analyze, i.e. iterations of the function to be run before the result is analyzed
    maxIterations = 1;

    % increase i_and_count's second entry by the result of myModeAnalyze
    % myModeAnalyze is fed the audio array, audio, and the parameters
    i_and_count(2) = i_and_count(2) + myModeAnalyze(audio, params);
```

Next, define the main audio processing function, `myModeAnalyze`, which must take an audio array and analysis parameters as input and output a number to be added to `i_and_count`'s second entry.
This output number should be positive if the result implies a notification to be necessary, decrease in opposite cases, or be zero in neutral cases.

```Matlab
function result = myModeAnalyze(audio, params)
    % set the default neutral return
    result = 0;
    % process the audio array using the params
    if someFunction(audio, params) == someResult
        result = 1;
    end
end
```

#### Calibration

Now, the calibration process needs to be set up.
Inside `callCalibrate`, include a block for the mode, again identified via a string.
This block simply needs to include a line to set the `params` variable by calling the calibration function.
The calibration function, `myModeCalibrate`, needs to accept an `audioDeviceReader` object and the name of the parameter to be used in the config file.
Within `callCalibrate`, the name is the `opts` variable.
The function should output the parameters used for analysis and the name.

The `audioDeviceReader` object is used to read the microphone and create an audio array.
To change the length of audio used to calibrate, change the `SamplesPerFrame` property of the `audioDeviceReader`.
To start recording, use the `step` function on the `audioDeviceReader`.

In the case of multiple recordings being required with different instructions, add another block in `callCalibrate` with name `mymode2` with 2 being the number identifying the recording.
This can then call another calibration function, e.g. `myModeCalibrate2`.
From here, you can run your second calibration.

The block(s) should look something like this:

```Matlab
elseif mode == "mymode"
    params = myModeCalibrate(In, opts);
elseif mode == "mymode2"
    params = myModeCalibrate2(In, opts);
```

The calibration function:

```Matlab
function param, name = myModeCalibrate(In, name)
    % set recording length e.g. to 3 seconds
    In.SamplesPerFrame = In.SampleRate * 3;
    % record the audio
    audio = step(In);
    % analyze and set calibration parameter
    param = someFunction(audio);
end
```

Calibration instructions need to be hardcoded in the Java class that calls them:
TODO.

#### Testing

To simply test your scripts in MATLAB, you can call the functions directly.

For calibration, call `callCalibrate`:

```Matlab
parameter = callCalibrate("mymode")
```

For analysis, call `realTimeAudioProcessingFunction`:

```Matlab
realTimeAudioProcessingFunction("mymode", parameter)
```

#### Examples

For simple examples for analysis and calibration functions, look at `noiseGateAnalyze.m` and `noiseGateCalibrate.m`.
For analysis and calibration function skeletons, look at `exampleAnalyzeSkeleton.m` and `exampleCalibrateSkeleton.m` in the `examples` directory.
