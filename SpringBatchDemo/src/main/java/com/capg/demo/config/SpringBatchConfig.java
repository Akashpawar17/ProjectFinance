package com.capg.demo.config;

import javax.batch.api.chunk.ItemProcessor;
import javax.batch.api.chunk.ItemReader;
import javax.batch.api.chunk.ItemWriter;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.capg.demo.model.User;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {
	
	
	
	@Bean
	public Job job(JobBuilderFactory jobBuilderFactory,StepBuilderFactory stepBuilderFactory,org.springframework.batch.item.ItemReader<User> itemReader,org.springframework.batch.item.ItemProcessor<User, User> itemProcessor,org.springframework.batch.item.ItemWriter<User> itemWriter) {
		Step step=stepBuilderFactory.get("ETl-filr-load")
				.<User,User>chunk(100)
				.reader(itemReader)
				.processor(itemProcessor)
				.writer(itemWriter)
				.build()
				
				;
		
	return	jobBuilderFactory.get("ETL-Load")
		.incrementer(new RunIdIncrementer())
		.start(step)
		.build();
	}
	
	//IN ORDER TO USE READER AND WRITER WE HAV TO IMPLEMENT THAT
	//IN ORDER TO USER THAT FILE WE HAVE TO USE FLATFILEITEMREADER
	@Bean
	public FlatFileItemReader<User> fileItemReader(@Value("{$input}") Resource resource)
	{
		//INORDER TO USE THAT PARTICULAR FILE
		FlatFileItemReader<User> flatFileItemReader=new FlatFileItemReader<>();
		//add the resource
		flatFileItemReader.setResource(resource);
		//set the default values
		flatFileItemReader.setName("CSV-Reader");
		//set the no.of line if ther is any issue
		flatFileItemReader.setLinesToSkip(1);
		//finally we need to add(map) that to the user class
		flatFileItemReader.setLineMapper(LineMapper());
		
		return flatFileItemReader;
		
	}

	@Bean
	public LineMapper<User> LineMapper() {
		//we need to create defaultlinemapper
		DefaultLineMapper<User> defaultLineMapper =new DefaultLineMapper<>();
		//it is a comma seperated file so we need to add a tokenizerlinemapeer
		DelimitedLineTokenizer lineTokenizer=new DelimitedLineTokenizer();
		lineTokenizer.setDelimiter(",");
	lineTokenizer.setStrict(false);
		lineTokenizer.setNames(new String[] {"id","name","dept","salary"});

		//we need to set each field with particular pojo(User class)
		BeanWrapperFieldSetMapper<User> fieldSetMapper=new BeanWrapperFieldSetMapper<>();
		fieldSetMapper.setTargetType(User.class);
		defaultLineMapper.setLineTokenizer(lineTokenizer);
		 defaultLineMapper.setFieldSetMapper(fieldSetMapper);
		
		
		return defaultLineMapper;
	}
	
	
	
	
	
	
	
	
	
	
	

}
