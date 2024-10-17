.PHONY build-debugger:
build-debugger:
	@echo "Building debugger..."
	@docker build -t ibakuman/java-debugger docker/debugger