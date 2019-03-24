default:
	docker build -t amazing-co .

run:
	docker run -it -p 8080:8080 --rm -v $(pwd):/service -v ~/.gradle:/root/.gradle amazing-co

test:
	docker run -it --rm -v $(pwd):/service -v ~/.gradle:/root/.gradle amazing-co ./gradlew test