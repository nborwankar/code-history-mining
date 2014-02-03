package http

class AllTemplates {
	static changeSizeChartTemplate = template("changes-size-chart.html")
	static amountOfCommittersChartTemplate = template("amount-of-committers-chart.html")
	static amountOfFilesInCommitChartTemplate = template("amount-of-files-in-commit-chart.html")
	static amountOfCommitsTreemapTemplate = template("treemap.html")
	static filesInTheSameCommitGraphTemplate = template("files-in-same-commit-graph.html")
	static committersChangingFilesGraphTemplate = template("committers-changing-files-graph.html")
	static committersChangingSameFilesGraphTemplate = template("committers-changing-same-files-graph.html")
	static commitTimePunchcardTemplate = template("commit-time-punchcard.html")
	static timeBetweenCommitsHistogramTemplate = template("time-between-commits-histogram.html")
	static commitMessageWordCloudTemplate = template("wordcloud.html")


	private static Template template(String fileName) {
		new Template(HttpUtil.readFile(fileName)).inlineImports(HttpUtil.&readFile)
	}
}
