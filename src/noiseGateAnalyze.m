function isAbove = noiseGateAnalyze(audio, threshold)
    % NOISEGATEANALYZE Check whether any value in audio is above threshold
    %
    % noiseGateAnalyze(audio, 0.5)
    % 
    % Parameters:
    % * audio:     audio array
    % * threshold: noise gate threshold
    %
    % Returns:
    % * isAbove: 0 for no values in audio above threshold, 1 otherwise
    isAbove = 0;
    if any(audio > threshold)
        isAbove = 1;
    end
end
