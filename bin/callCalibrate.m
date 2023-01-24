function params = callCalibrate(mode)
    % CALLCALIBRATE call calibration function
    % 
    % callCalibrate("noisegate")
    %
    % Parameters:
    % mode: mode to use, "lisp" and "noisegate" supported
    %
    % Returns:
    % params: calibration output parameters

    In = audioDeviceReader;
    In.Device = "default";
    % set default frame length to 1 second
    In.SamplesPerFrame = In.SampleRate; 

    if mode == "lisp"
        params = lispCalibrate(In);
    elseif mode == "noisegate"
        params = noiseGateCalibrate(In);
    else
        error("Unknown mode passed!")
    end
end
