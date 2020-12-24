run:
	./gradlew build -x test
	java -jar build/libs/nubank-authorizer-all.jar

test:
	./gradlew test
