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
    isAbove = 0; % the default output if nothing is detected should be 0
    % for our noise gate we check if any sample within the audio array is
    % above our threshold
    if any(audio > threshold)
        % if this is the case we return one
        isAbove = 1;
    end
    % in case we'd want to support an additional block to detect e.g. a
    % correct pronunciation we could include another block here which sets
    % isAbove = -1;
    % for example:
    % if any(audio < threshold)
    %     isAbove = -1;
    % end
end
