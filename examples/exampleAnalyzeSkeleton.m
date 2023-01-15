function exampleHit = exampleAnalyzeSkeleton(audio, exampleInputParameter)
    % EXAMPLEANALYZESKELETON an example analysis function skeleton
    %
    % exampleAnalyzeSkeleton(audio, exampleInputParameter)
    % 
    % Parameters:
    % * audio:                 audio array
    % * exampleInputParameter: parameters required for analysis
    %
    % Returns:
    % * exampleHit: number signifying what was detected

    %% Default return
    % Here, set what is returned by default, e.g. for a neutral result.
    exampleHit = 0;

    %% Analysis
    % Process the audio with whatever function of your choice. This usually
    % won't be a single line.
    processingResult = myAudioProcessingFunction(audio, ...
        exampleInputParameter);
    processingResult = myAudioProcessingFunction2(audio, ...
        exampleInputParameter);

    %% Threshold checking
    % Finally, once your audio processing step is done, you can compare it
    % to some threshold and change the returned value.
    % Below or above some threshold, your script should detect a hit of
    % whatever you are trying to detect. This should return a positive
    % value.
    if processingResult > myThreshold
        exampleHit = 1;
        % Once you're done with your detection, return. This prevents
        % unwanted additional checks from being performed.
        return
    end
    % If the above wasn't a hit, you may want to compare to another
    % threshold. Additionally, you may even want the result to compensate
    % for a hit, in which case should return a negative value.
    if processingResult > myThreshold2
        exampleHit = -1;
        return
    end
end
