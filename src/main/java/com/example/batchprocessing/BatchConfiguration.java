package com.example.batchprocessing;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;

// tag::setup[]
@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	// end::setup[]

	// tag::readerwriterprocessor[]
	@Autowired 
	AbrechnungGevoTasklet abrGvTasklet;
	
	@Bean
	public FlatFileItemReader<Person> reader() {
		return new FlatFileItemReaderBuilder<Person>()
			.name("personItemReader")
			.resource(new ClassPathResource("sample-data.csv"))
			.delimited()
			.names(new String[]{"firstName", "lastName"})
			.fieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {{
				setTargetType(Person.class);
			}})
			.build();
	}
	
	
	@Bean
	public JdbcBatchItemWriter<Gevo> writerdb2(DataSource dataSource){
		return new JdbcBatchItemWriterBuilder<Gevo>()
				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
				.dataSource(dataSource)
				.sql(SQL2)
				.build();
				
	}
	
	private final String SQL2 = "insert into db2_gevo (idgevo,idkonto,idabrechnung,pem,lakey,gevoart,stornoart,betrag,tilgbezug)"
			+ "values(:idgevo,:idKonto,:idAbrechnung,:pem,:lakey,:gevoart,:stornoart,:betrag,:tilgbezug)";
	@Bean
	public JdbcCursorItemReader<KontoAbrechnung> readerdb2(DataSource dataSource){
		return new JdbcCursorItemReaderBuilder<KontoAbrechnung>()
				.name("readerdb2")
				.dataSource(dataSource)
				.sql(SQL1)
				.rowMapper(new KontoAbrechnungRowMapper())
				.build();					
	}

	private final String SQL1 = "select K.*, A.* from db2_Konto K join db2_abrechnung A on A.idkonto = K.idkonto order by A.Idkonto, A.Idabrechnung";
	
	@Bean
	public PersonItemProcessor processor() {
		return new PersonItemProcessor();
	}
	
	@Bean
	public AbrechnungGevoItemProcessor processordb2() {
		return new AbrechnungGevoItemProcessor();
	}
	
	

	@Bean
	public JdbcBatchItemWriter<Person> writer(DataSource dataSource) {
		return new JdbcBatchItemWriterBuilder<Person>()
			.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
			.sql("INSERT INTO people (first_name, last_name) VALUES (:firstName, :lastName)")
			.dataSource(dataSource)
			.build();
	}
	// end::readerwriterprocessor[]

	// tag::jobstep[]
	@Bean
	public Job importUserJob(JobCompletionNotificationListener listener, Step step1, Step step3) {
		return jobBuilderFactory.get("importUserJob")
			.incrementer(new RunIdIncrementer())
			.listener(listener)
			//.flow(step1)
			.start(step2())
			.next(step3)
		//	.end()
			.build();
	}

	@Bean
	public Step step1(JdbcBatchItemWriter<Person> writer) {
		return stepBuilderFactory.get("step1")
			.<Person, Person> chunk(10)
			.reader(reader())
			.processor(processor())
			.writer(writer)
			.build();
	}
	
	@Bean
	public Step step2() {
		return stepBuilderFactory.get("step2")
		.tasklet(abrGvTasklet)
		.build();
	}
	
	@Bean	
	public Step step3(JdbcCursorItemReader<KontoAbrechnung> readerdb2
			, JdbcBatchItemWriter<Gevo> writerdb2
			,Step3Listener listener3
			) {
		return stepBuilderFactory.get("step3")
			.<KontoAbrechnung,Gevo>chunk(2)		    
			.reader(readerdb2)
			.processor(processordb2())
			.writer(writerdb2)
			.listener(listener3)
			.build();
			
			
	}
	
	
	
	
	// end::jobstep[
}
   