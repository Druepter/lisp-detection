function [params, name] = callCalibrate(mode, opts)
    % CALLCALIBRATE call calibration function
    % 
    % callCalibrate("noisegate")
    % 
    % This doesn't do the full calibration. The function needs to be called
    % the same number of times as desired parameters.
    %
    % Parameters:
    % mode: mode to use, "lisp" and "noisegate" supported
    % opts: options to be passed to calibrate function
    %
    % Returns:
    % params: calibration output parameters
    % name:   parameter name

    In = audioDeviceReader;
    In.Device = "default";
    % set default frame length to 1 second
    In.SamplesPerFrame = In.SampleRate; 

    disp("In Matlab");

    if mode == "lisp"
        [name, params] = lispCalibrate(In, opts);
    elseif mode == "noisegate"
        [name, params] = noiseGateCalibrate(In, opts);
    else
        error("Unknown mode passed!")
    end
    params = [params];
    
end
