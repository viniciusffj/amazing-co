default:
	docker build -t amazing-co .

run:
	docker run -it -p 8080:8080 -v $(pwd):/service -v ~/.gradle:/root/.gradle amazing-co