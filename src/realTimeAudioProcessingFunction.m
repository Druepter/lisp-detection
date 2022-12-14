function [] = realTimeAudioProcessingFunction()
    % config file means no params need to get passed
    % alternatively we do the config file reading separately
    % and pass the params but this is already done at least
    %% initialize audio devices
    % input audio device
    In = audioDeviceReader;
    In.Device = "default";
    
    % set frame window in samples
    frameLength = 0.5; % seconds
    sampleRate = In.SampleRate;
    In.SamplesPerFrame = sampleRate * frameLength;
    
    % output audio device
    Out = audioDeviceWriter;
    Out.Device = "default";
    
    %% read calibration file
    fileName = fopen('calibration.config');
    formatSpec = '%q';
    % save file to cell array
    calibration = textscan(fileName,formatSpec);
    
    % get mode from cell array 
    mode = calibration{1}(1, 1);
    mode = string(mode);
    
    % get normalFreqs from cell array
    normalFreqs = calibration{1}(2, 1);
    normalFreqs = cell2mat(normalFreqs);
    normalFreqs = strsplit(normalFreqs,',');
    normalFreqs = str2double(normalFreqs);
    
    % get lispFreqs from cell array
    lispFreqs = calibration{1}(3, 1);
    lispFreqs = cell2mat(lispFreqs);
    lispFreqs = strsplit(lispFreqs,',');
    lispFreqs = str2double(lispFreqs);
    
    % get lispFreqs from cell array
    restFreqs = calibration{1}(4, 1);
    restFreqs = cell2mat(restFreqs);
    restFreqs = strsplit(restFreqs,',');
    restFreqs = str2double(restFreqs);
    
    params = [normalFreqs', lispFreqs', restFreqs'];
    
    %Frequenzen welche in die Datei geschrieben werden
    %Hier dienen die gelesenen gerade als Platzhalten
    %Die müssen im Verlauf der Entwicklung durch die Kalibierten ersetzt werden
    normalFreqFristValue = string(normalFreqs(1, 1));
    normalFreqSecondValue = string(normalFreqs(1, 2));
    lispFreqsFirstValue = string(lispFreqs(1, 1));
    lispFreqsSecondValue = string(lispFreqs(1, 2));
    restFreqsFirstValue = string(restFreqs(1, 1));
    restFreqsSecondValue = string(restFreqs(1, 2));
    
    %Öffene Config Datei im Schreibmodus
    fileID = fopen('calibration.config', 'w');
    %Baue String zusammen, welcher in die Config Datei geschrieben wird
    dataToWriteInConfigFile = strcat("""lisp"" """, normalFreqFristValue, ", ", normalFreqSecondValue, """ """, lispFreqsFirstValue, ", ", lispFreqsSecondValue, """ """, restFreqsFirstValue, ", ", restFreqsSecondValue, """");
    fprintf(fileID, dataToWriteInConfigFile);
    fclose(fileID);
    
    %% loop over analyze
    i = 0;
    count = 0;
    
    tic
    while toc
         x = step(In);
         y = x;
    
         step(Out, y);
    
         % actually run the analyze
         i, count = callAnalyze(mode, i, count, x, params);
    end    
end
