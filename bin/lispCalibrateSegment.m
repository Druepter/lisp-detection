function freqs = lispCalibrateSegment(audio, bandPass)
    % LISPCALIBRATESEGMENT lisp calibration for a given segment
    %
    % lispCalibrateSegment(audio, 1000)
    %
    % Parameters:
    % * audio:    audio array
    % * bandPass: band pass frequencies
    %
    % Returns:
    % * freqs: 2 element array containing detected frequencies for sound
    audioF = abs(fft(audio));
    ofInterest = audioF(bandPass(1):bandPass(2), :);
    % we only need the locations of peaks as these correspond to
    % frequencies
    [~, locs] = findpeaks(ofInterest, 'MaxPeakWidth', 1000, ...
        'MinPeakHeight', max(ofInterest) / 4);
    freqs = [min(locs) + bandPass(1), max(locs) + bandPass(1)];
end