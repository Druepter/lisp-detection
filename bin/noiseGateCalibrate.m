function threshold = noiseGateCalibrate(In)
    % NOISEGATECALIBRATE Find noise gate threshold
    % 
    % noiseGateCalibrate(In)
    %
    % Parameters:
    % * In: input audio device
    % 
    % Returns:
    % * threshold: noise gate threshold; 4 * maximum of 2s audio recording

    disp("Please remain silent for 3 seconds.") % instructions
    % we're not actually doing 3 seconds but we need a buffer for reading
    pause(0.5); % sleep for a second before recording - to remove e.g. key presses
    In.SamplesPerFrame = In.SampleRate * 2; % 2 second recording
    
    % start saving the audio array
    audio = step(In);
    disp("Done.")
    % set our analysis parameter for the noise gate
    % this is just the maximum of the audio array multiplied by an
    % arbitrary number
    threshold = max(audio) * 4;
end
