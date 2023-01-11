function [m] = realTimeAudioProcessingFunction(mode, params)
    % REALTIMEAUDIOPROCESSINGFUNCTION
    %
    % realTimeAudioProcessingFunction("lisp", [1050, 5500, 1000; 1350, 6500, 22050])
    %
    % realTimeAudioProcessingFunction("noisegate", 0.1)
    %
    % Params:
    % * mode:   audio processing mode [lisp, noisegate]
    % * params: parameters for audio processing, mode-dependent
    %
    % Returns:
    % * m: true, is only returned once something is detected
    In = audioDeviceReader; % input audio device
    In.Device = "default";
    
    % set frame window in samples
    frameLength = 0.5; % seconds
    sampleRate = In.SampleRate;
    In.SamplesPerFrame = sampleRate * frameLength;
    
    % output audio device
    Out = audioDeviceWriter;
    Out.Device = "default";
    
%% This was how we read the params for lisp detected - leaving here for now
%     %%Split Parameters and convert them to double array
%     normalFreqs = strsplit(normalFreqs,',');
%     normalFreqs = str2double(normalFreqs);
% 
%     lispFreqs = strsplit(lispFreqs,',');
%     lispFreqs = str2double(lispFreqs);
%     
%     restFreqs = strsplit(restFreqs,',');
%     restFreqs = str2double(restFreqs);
%     
% 
%     %%Build parameter array to pass it into callAnalyze Function
%     params = [normalFreqs', lispFreqs', restFreqs'];
    
    
    
    %% loop over analyze
    i_and_count = [0, 0];
    

    m = true;

    tic
    while toc

         disp("Schleife läuft");
         x = step(In);
         y = x;
    
         step(Out, y);
    
         % actually run the analyze
         i_and_count = callAnalyze(mode, i_and_count, x, params);

         % exit the loop by returning
         if isa(i_and_count, "logical")
             return
         end

    end    
end
