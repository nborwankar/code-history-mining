#!/usr/bin/ruby

class String
  def end_index(s)
    index(s) + s.size
  end
  def end_rindex(s)
    rindex(s) + s.size
  end
end


def extract_content_from(file_name)
  html = File.read(file_name)

  from = html.index('<style')
  to = html.end_index('</style>')
  style = html[from..to]

  from = html.rindex('<script')
  to = html.end_rindex('</script>')
  script = html[from..to-1]

  [style, script]
end


def merge_into(src_path, template_file, target_file, files_with_fixes)
  remove_margin_style = Proc.new { |html|
    html.gsub!(/margin:.*?;/, '')
  }

  remove_header_span = Proc.new { |html|
    html.gsub!(/var header.*?;/m, '')
    html.gsub!(/headerSpan\..*?;/m, '')
    html.gsub!(/[\s\t]header\..*?;/m, '')
  }

  reduce_width = Proc.new { |html|
    html.gsub!(/var width =.*?;/, 'var width = 800;')
  }
  common_fixes = [remove_margin_style, remove_header_span, reduce_width]


  html = File.read(template_file)
  files_with_fixes.each { |file, fixes|
    p "Processing #{file}"
    style, script = extract_content_from(src_path + file)

    (fixes + common_fixes).each { |fix|
      fix.call(style)
      fix.call(script)
    }

    html.insert(html.end_rindex("</style>"), style)
    html.insert(html.end_rindex("</script>"), script)
  }
  File.open(target_file, "w") { |f| f.write(html) }
end