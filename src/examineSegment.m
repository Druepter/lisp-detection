function isHit = examineSegment(fftMagnitude, segment, rest)
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
