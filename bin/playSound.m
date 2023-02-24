function [] = playSound(length)
    % PLAYSOUND Play a simple sound of given length
    % 
    % playSound(0.5)
    % 
    % Parameters:
    % * length: Length of sound in seconds. The sound played will be a 500
    %           Hz sine.
    % 
    % No return.
    f = 500; % sound frequency in Hz
    % sampling frequency needs to be above nyquist but as low as possible
    Fs = round(2 * f + 2);
    % linear space of length corresponding to length seconds
    % this will be fed to a sine function to generate the sound signal
    t = linspace(0, length, round(Fs * length));
    % play sound
    sound(sin(2 * pi * f * t), Fs)
end