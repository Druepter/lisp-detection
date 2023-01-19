function exampleOutputParameter = exampleCalibrateSkeleton(In)
    % EXAMPLECALIBRATESKELETON an example calibration function skeleton
    % 
    % exampleCalibrateSkeleton(In)
    %
    % Parameters:
    % * In: input audio device
    % 
    % Returns:
    % * exampleOutputParameter: the parameter required for analysis

    %% Recording variables
    % Here's where you put your calibration instructions. For example, you
    % might want to ask for a second of silence.
    instructions = "";
    % This sets the length in seconds of the audio array to be recorded.
    recordingLength = 1;
    % To remove key presses or allow time to read, we pause for a given
    % time frame. This parameters sets the pause time in seconds.
    pauseLength = 0.5;

    %% Recording steps
    disp(instructions)
    pause(pauseLength);
    In.SamplesPerFrame = In.SampleRate * recordingLength;
    
    % In this step, we record the audio array.
    audio = step(In);

    % Let the user know the recording is done and they are free to make
    % whatever sounds they want.
    disp("Done.")

    %% Audio processing
    % Finally, start processing your audio. Make sure to adjust which
    % variable you want the function to return in the first line.
    exampleOutputParameter = myAudioProcessingFunction(audio);
end
