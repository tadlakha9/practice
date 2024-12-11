package com.example.practice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootApplication
public class PracticeApplication {

	public static void main(String[] args) {
		SpringApplication.run(PracticeApplication.class, args);
//		Scanner sc = new Scanner(System.in);
//
//		int input = Integer.parseInt(sc.nextLine());
//		String inputString = sc.nextLine();
//
//		System.out.println(computeWinningTeam(inputString));
	}

//	public static String computeWinningTeam(String inputString){
//
//		while(inputString.length()>1){
//			String finalInputString = inputString;
//			inputString = IntStream.range(0, inputString.length())
//					.filter(i -> i%2!=0)
//					.mapToObj(i -> String.valueOf(finalInputString.charAt(i)))
//					.collect(Collectors.joining());
//		}
//		return inputString;
//	}

}
