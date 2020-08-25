# spring-boot-starter-slate

use slate in spring boot java application 

see [https://github.com/slatedocs/slate](https://github.com/slatedocs/slate)

# import

```
<dependency>
   <groupId>com.github.duwanqiebi</groupId>
   <artifactId>spring-boot-starter-slate</artifactId>
   <version>1.0.1</version>
</dependency>
```

## config

```
slate:
  enable: true
  defaultTitle: API DOC
  contextPath: /slate/*
  resourcePath: slate
  index: index.html.md
  includes:
    - _errors.md
  languages:
    - ruby
    - python
    - shell
    - javascript
```
