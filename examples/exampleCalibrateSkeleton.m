function [exampleOutputParameter, name] = exampleCalibrateSkeleton(In, name)
    % EXAMPLECALIBRATESKELETON an example calibration function skeleton
    % 
    % exampleCalibrateSkeleton(In, "name")
    %
    % Parameters:
    % * In:   input audio device
    % * name: parameter name to be used in config
    % 
    % Returns:
    % * exampleOutputParameter: the parameter required for analysis
    % * name:                   parameter name to be used in config

    %% Recording variables
    % This sets the length in seconds of the audio array to be recorded.
    recordingLength = 1;

    %% Recording steps
    In.SamplesPerFrame = In.SampleRate * recordingLength;
    
    % In this step, we record the audio array.
    audio = step(In);

    %% Audio processing
    % Finally, start processing your audio. Make sure to adjust which
    % variable you want the function to return in the first line.
    exampleOutputParameter = myAudioProcessingFunction(audio);
end
