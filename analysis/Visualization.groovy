package analysis
import events.FileChangeEvent
import groovy.transform.Immutable

import static http.AllTemplates.*

@Immutable
class Visualization {
	final String name
	final Closure<String> generate


	static changeSizeChart = new Visualization("Change Size Chart", { Context context ->
		def json = Analysis.createJson_ChangeSize_Chart(context.events, context.checkIfCancelled)
		changeSizeChartTemplate.fillData(json).fillProjectName(context.projectName).text
	})

	static amountOfCommittersChart = new Visualization("Amount Of Committers Chart", { Context context ->
		def json = Analysis.createJson_AmountOfCommitters_Chart(context.events, context.checkIfCancelled)
		amountOfCommittersChartTemplate.fillData(json).fillProjectName(context.projectName).text
	})

	static amountOfFilesInCommitChart = new Visualization("Amount Of Files In Commit Chart", { Context context ->
		def json = Analysis.createJson_AverageAmountOfFilesInCommit_Chart(context.events, context.checkIfCancelled)
		amountOfFilesInCommitChartTemplate.fillData(json).fillProjectName(context.projectName).text
	})

	static amountOfCommitsTreemap = new Visualization("Amount Of Commits Treemap", { Context context ->
		def json = Analysis.TreeMapView.createJson_AmountOfChangeInFolders_TreeMap(context.events, context.checkIfCancelled)
		amountOfCommitsTreemapTemplate.fillData(json).fillProjectName(context.projectName).text
		// TODO try sunburst layout? (http://bl.ocks.org/mbostock/4063423)
	})

	static filesInTheSameCommitGraph = new Visualization("Files In The Same Commit Graph", { Context context ->
		def json = Analysis.createJson_FilesInTheSameCommit_Graph(context.events, context.checkIfCancelled)
		filesInTheSameCommitGraphTemplate.fillData(json).fillProjectName(context.projectName).text
	})

	static committersChangingFilesGraph = new Visualization("Committers Changing Files Graph", { Context context ->
		def json = Analysis.committersChangingFilesGraph(context.events, context.checkIfCancelled)
		committersChangingFilesGraphTemplate.fillData(json).fillProjectName(context.projectName).text
	})

	static committersChangingSameFilesGraph = new Visualization("Committers Changing Same Files Graph", { Context context ->
		def json = Analysis.createJson_AuthorConnectionsThroughChangedFiles_Graph(context.events, context.checkIfCancelled)
		committersChangingSameFilesGraphTemplate.fillData(json).fillProjectName(context.projectName).text
	})

	static commitTimePunchcard = new Visualization("Commit Time Punchcard", { Context context ->
		def json = Analysis.createJson_CommitsByDayOfWeekAndTime_PunchCard(context.events, context.checkIfCancelled)
		commitTimePunchcardTemplate.fillData(json).fillProjectName(context.projectName).text
	})

	static timeBetweenCommitsHistogram = new Visualization("Time Between Commits Histogram", { Context context ->
		def json = Analysis.createJson_TimeBetweenCommits_Histogram(context.events, context.checkIfCancelled)
		timeBetweenCommitsHistogramTemplate.fillData(json).fillProjectName(context.projectName).text
	})

	static commitMessageWordCloud = new Visualization("Commit Messages Word Cloud", { Context context ->
		def json = Analysis.createJson_CommitComments_WordCloud(context.events, context.checkIfCancelled)
		commitMessageWordCloudTemplate.fillData(json).fillProjectName(context.projectName).text
	})


	static class Context {
		final List<FileChangeEvent> events
		final String projectName
		final Closure<Boolean> checkIfCancelled

		Context(List<FileChangeEvent> events, String projectName, Closure checkIfCancelled = {}) {
			this.events = events
			this.projectName = projectName
			this.checkIfCancelled = checkIfCancelled
		}
	}
}
