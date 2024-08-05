#include "Logger.h"

vector<LogEntry> Logger::logs;

string CurrentDateAndTime()
{
	time_t now = chrono::system_clock::to_time_t(chrono::system_clock::now());
	string output(30, '\0');
	strftime(&output[0], output.size(), "%b/%d/%Y %H:%M:%S", localtime(&now));
	return output;
}

void Logger::Log(const string& msg) 
{
	LogEntry logEntry;
	logEntry.type = LOG_INFO;
	logEntry.logMsg = "LOG: [" + CurrentDateAndTime() + "]: " + msg;
	cout << "\x1b[32m" << logEntry.logMsg << "\033[0m" << endl;

	logs.push_back(logEntry);
}

void Logger::Err(const string& msg) 
{
	LogEntry logEntry;
	logEntry.type = LOG_ERROR;
	logEntry.logMsg = "ERR: [" + CurrentDateAndTime() + "]: " + msg;
	cout << "\x1b[91m" << logEntry.logMsg << "\033[0m" << endl;

	logs.push_back(logEntry);
}