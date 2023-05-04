# openai-java

OpenAi GPT4 API for Java

## How to use

### Maven

```xml

<dependency>
    <groupId>xyz.felh</groupId>
    <artifactId>openai-service</artifactId>
    <version>1.1.1</version>
</dependency>

<!-- 如果需要计算tokens数量 -->
<dependency>
    <groupId>xyz.felh</groupId>
    <artifactId>gpt3-tokenizer</artifactId>
    <version>1.1.1</version>
</dependency>
```

### Gradle

```yaml
implementation group: 'xyz.felh', name: 'openai-java', version: '1.1.1'
```

### sbt

```javascript
libraryDependencies += "xyz.felh" % "openai-java" % "1.1.1"
```

## Supported APIs

- [Models](https://platform.openai.com/docs/api-reference/models)
- [Completions](https://platform.openai.com/docs/api-reference/completions)
- [Chat Completions(2023-04-18 support stream)](https://platform.openai.com/docs/api-reference/chat/create)
- [Edits](https://platform.openai.com/docs/api-reference/edits)
- [Embeddings](https://platform.openai.com/docs/api-reference/embeddings)
- [Images](https://platform.openai.com/docs/api-reference/images)
- [Audio](https://platform.openai.com/docs/api-reference/audio)
- [File](https://platform.openai.com/docs/api-reference/files)
- [Moderations](https://platform.openai.com/docs/api-reference/moderations)
- [Fine-tunes](https://platform.openai.com/docs/api-reference/fine-tunes)

## License

Published under the MIT License (https://github.com/forestwanglin/openai-java/blob/main/LICENSE)

