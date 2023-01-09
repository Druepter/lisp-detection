function threshold = noiseGateCalibrate(In)
    % NOISEGATECALIBRATE Find noise gate threshold
    % 
    % noiseGateCalibrate(In)
    %
    % Parameters:
    % * In: input audio device
    % 
    % Returns:
    % * threshold: noise gate threshold; 2 * maximum of 1s audio recording
    audio = step(In);

    threshold = maximum(audio) * 2;
end
