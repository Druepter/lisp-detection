function [name, threshold] = noiseGateCalibrate(In, name)
    % NOISEGATECALIBRATE Find noise gate threshold
    % 
    % noiseGateCalibrate(In, "threshold")
    %
    % Parameters:
    % * In:   input audio device
    % * name: "threshold" or whatever name for the param
    % 
    % Returns:
    % * name:      name
    % * threshold: noise gate threshold; 4 * maximum of 2s audio recording

    pause(0.5); % sleep for a second before recording - to remove e.g. key presses
    In.SamplesPerFrame = In.SampleRate * 2; % 2 second recording
    
    % start saving the audio array
    audio = step(In);
    % set our analysis parameter for the noise gate
    % this is just the maximum of the audio array multiplied by an
    % arbitrary number
    threshold = max(audio) * 4;
end
