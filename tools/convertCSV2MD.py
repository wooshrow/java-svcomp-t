#
# Utility to convert csv-file to a markdown table.
#
import sys
import pandas as pd

# Load your CSV file
df = pd.read_csv(sys.argv[1])
# Convert and save as a Markdown file
df.to_markdown('output.md', index=False)
