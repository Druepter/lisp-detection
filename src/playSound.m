function [] = playSound(length)
    % sound frequency in Hz
    f = 500;
    % sampling frequency needs to be above nyquist but as low as possible
    Fs = round(2 * f + 2);
    % linear space of length corresponding to length seconds
    % this will be fed to a sine function to generate the sound signal
    t = linspace(0, length, round(Fs * length));
    % play sound
    sound(sin(2 * pi * f * t), Fs)
end