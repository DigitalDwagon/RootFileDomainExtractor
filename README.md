# Root File Domain Extractor
This program takes in zone files (in the form of uncompressed text files) in the "todo" directory in the folder it's in, and processes the messy zone files into a simple list of domains, which is more favorable for most projects.

Known issues:
- This is slow when working with large or many files, as there's no parallelization
- Domains are only deduplicated in buckets of 3 million, and not against each other, to reduce ram usage
