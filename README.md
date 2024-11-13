# Note
As of the time of creating the Repo, Claude Anthropic and OpenAI API's do not support PDF upload,
therefore we need to convert and split the PDF that we want to have Analyzed into multiple images, 
and send them to the API's.  
This is not supported in Spring AI as well.  
In the future all of the things mentioned above will be probably changed and PDF uploads will be directly supported.  

# How to use this project?
In order to use this project, you need to register for OpenAI and Claude Anthropic and add credit to your accounts.  
1. OpenAI link - https://platform.openai.com/settings/organization/billing/overview
2. Claude Anthropic link - https://console.anthropic.com/settings/billing  
3. Generate API keys for both of them through their consoles  

After that you need to set the following environment variables (the API keys) in order to run
the project successfully:
1. `OPENAI_API_KEY`
2. `ANTHROPIC_API_KEY`

Start the project and you can use Postman/cURL to test the two endpoints:
1. Test OpenAI endpoint
```
curl --location 'http://localhost:8080/api/v1/upload/openai/pdf' \
--form 'file=@"/E:/Projects/spring-ai/documents/example-rate-confirmation.pdf"'
```
2. Test Claude Anthropic endpoint
```
curl --location 'http://localhost:8080/api/v1/upload/anthropic/pdf' \
--form 'file=@"/E:/Projects/spring-ai/documents/example-rate-confirmation.pdf"'
```

# Materials to use
## Several good usages of the Spring AI API
https://github.com/danvega/spring-into-ai    
https://spring.io/blog/2024/05/30/spring-ai-1-0-0-m1-released  
https://docs.spring.io/spring-ai/reference/api/chat/openai-chat.html
## How to convert pdf to images
https://stackoverflow.com/questions/23326562/convert-pdf-files-to-images-with-pdfbox  
https://github.com/LibrePDF/OpenPDF/issues/346 - not working, just posting the link to track, using PDFBox instead
## Issues
https://stackoverflow.com/questions/78121525/spring-ai-opentezi teai-error-extracting-response-of-type-openaiapiembeddinglist  
https://stackoverflow.com/questions/15414259/java-bufferedimage-to-byte-array-and-back

## How to use Anthropic
https://www.danvega.dev/blog/claude-sonnet-spring-ai  
https://docs.anthropic.com/en/api/getting-started  
https://docs.anthropic.com/en/api/messages-examples#vision  
https://github.com/danvega/hello-claude

## How to use Multiple LLMs with Spring Boot
https://www.danvega.dev/blog/spring-ai-multiple-llms


 