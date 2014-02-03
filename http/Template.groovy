package http

import java.util.regex.Matcher

class Template {
	final String text

	Template(String text) {
		this.text = text
	}

	Template fillProjectName(String projectName) {
		new Template(fillProjectNamePlaceholder(text, "\"$projectName\""))
	}

	Template fillData(String jsValue) {
		new Template(fillDataPlaceholder(text, jsValue))
	}

	Template inlineImports(Closure<String> readFile) {
		new Template(inlineJSLibraries(inlineStylesheets(text, readFile), readFile))
	}

	private static String fillProjectNamePlaceholder(String templateText, String projectName) {
		templateText.replaceFirst(/(?s)\/\*project_name_placeholder\*\/.*\/\*project_name_placeholder\*\//, Matcher.quoteReplacement(projectName))
	}

	private static String fillDataPlaceholder(String templateText, String jsValue) {
		templateText.replaceFirst(/(?s)\/\*data_placeholder\*\/.*\/\*data_placeholder\*\//, Matcher.quoteReplacement(jsValue))
	}

	private static String inlineStylesheets(String html, Closure<String> fileReader) {
		(html =~ /(?sm).*?<link.*? rel="stylesheet".*? href="(.*?)".*/).with{
			if (!matches()) html
			else inlineStylesheets(
					html.replaceFirst(/(?sm)\n*\s*?<link.* rel="stylesheet".* href="(${group(1)})".*?>/, "")
							.replaceFirst(/\s*?<\/head>/, "<style>${fileReader(group(1))}</style></head>"),
					fileReader
			)
		}
	}

	private static String inlineJSLibraries(String html, Closure<String> fileReader) {
		(html =~ /(?sm).*?<script src="(.*?)"><\/script>.*/).with{
			if (!matches()) html
			else inlineJSLibraries(
					html.replace("<script src=\"${group(1)}\"></script>", "<script>${fileReader(group(1))}</script>"),
					fileReader
			)
		}
	}

}
