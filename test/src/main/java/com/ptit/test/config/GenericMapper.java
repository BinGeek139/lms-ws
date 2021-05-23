package com.ptit.test.config;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GenericMapper {
	
	@Autowired
	private ModelMapper modelMapper;
	// map cả null sang
	public <T, E> E mapToType(T source, Class<E> typeDestination) {
		return modelMapper.map(source, typeDestination);
	}

	// map bỏ qua null
	public <T, E> E mapToTypeNotNullProperty(T source, Class<E> typeDestination) {
		modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull()).setMatchingStrategy(MatchingStrategies.STRICT);
		return modelMapper.map(source, typeDestination);
	}

	public <S, T> List<T> mapToListOfType(List<S> source, Class<T> targetClass) {
		return source.stream().map(item -> modelMapper.map(item, targetClass)).collect(Collectors.toList());
	}

	public <S, T> List<T> mapToListOfTypeNotNullProperty(List<S> source, Class<T> targetClass) {
		return source.stream().map(item -> mapToTypeNotNullProperty(item, targetClass)).collect(Collectors.toList());
	}

	// copy source đến đích bỏ qua null
	// dùng cho update request
	public <T, E> void mapSrcToDestNotNullProperty(T source, E destination) {
		modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull())
		.setMatchingStrategy(MatchingStrategies.STRICT);
		modelMapper.map(source, destination);
	}

	public <S, T> Page<T> toPage(Page<S> source, Class<T> targetClass, Pageable pageable) {
		modelMapper.getConfiguration().setAmbiguityIgnored(true);
		Page<T> page = new PageImpl<T>(
				source.stream().map(item -> modelMapper.map(item, targetClass)).collect(Collectors.toList()), pageable,
				source.getTotalElements());
		return page;
	}
}
