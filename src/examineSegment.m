function isHit = examineSegment(fftMagnitude, segment, rest)
    % EXAMINESEGMENT Examing an audio segment's FFT magnitude
    % 
    % Parameters:
    % * fftMagnitude: Audio segment's FFT magnitude.
    % * segment:      Frequency segment to be looked for.
    % * rest:         Frequency band to be used for calculations.
    % 
    % Returns:
    % * isHit: Logical whether a hit was detected.
    disp("Jetzt ist er in der Segment Funktion: " + rest(1));
    low = rest(1);
    high = rest(2);
    % normalized bandpass
    slicedFft = normalize(fftMagnitude(low:high));
    % scale the segment
    scaledSegment = zeros(length(segment));
    for i = 1:length(segment)
        scaledSegment(i) = segment(i) - low;
    end
    % we have to make sure the rest segment isn't greater than the whole length
    limitA = scaledSegment(1);
    limitB = min(scaledSegment(2), length(slicedFft));
    sliceMean = mean(slicedFft(limitA:limitB));
    if segment(2) == length(slicedFft)
        sliceMeanRest = mean(slicedFft(1:scaledSegment(1)));
    elseif segment(1) == 1
        sliceMeanRest = mean(slicedFft(scaledSegment(2):end));
    else
        sliceMeanRest = mean(cat(1, slicedFft(1:scaledSegment(1)), slicedFft(scaledSegment(2):end)));
    end
    sliceMeanDiff = sliceMean - sliceMeanRest;
    isHit = sliceMeanDiff > 0;
end
