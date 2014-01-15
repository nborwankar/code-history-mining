import historyreader.ChangeEventsReaderGitTest
import historyreader.CommitReaderGitTest
import historyreader.TextCompareProcessorTest
import liveplugin.testrunner.IntegrationTestsRunner

def tests = [TextCompareProcessorTest, CommitReaderGitTest, ChangeEventsReaderGitTest]
IntegrationTestsRunner.runIntegrationTests(tests, project, pluginPath)