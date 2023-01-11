function threshold = noiseGateCalibrate(In)
    % NOISEGATECALIBRATE Find noise gate threshold
    % 
    % noiseGateCalibrate(In)
    %
    % Parameters:
    % * In: input audio device
    % 
    % Returns:
    % * threshold: noise gate threshold; 4 * maximum of 1s audio recording

    In.SamplesPerFrame = In.SampleRate;

    audio = step(In);

    threshold = max(audio) * 4;
end
