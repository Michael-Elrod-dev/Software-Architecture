#pragma once

#include <iostream>
#include <string>
#include <vector>
#include <chrono>
#include <ctime>

using namespace std;

enum LogType
{
	LOG_INFO,
	LOG_WARNING,
	LOG_ERROR
};

struct LogEntry
{
	LogType type;
	string logMsg;
};

class Logger {

private:
	static vector<LogEntry> logs;

public:
	static void Log(const string& msg);
	static void Err(const string& msg);

};