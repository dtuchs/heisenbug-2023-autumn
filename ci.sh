            docker-compose -f docker-compose-e2e.yml up -d
            docker ps -a
            docker wait rococo-e2e
            exit_code=$(docker inspect -f '{{.State.ExitCode}}' rococo-e2e)
            echo "### Test logs ###"
            docker logs rococo-e2e
            docker-compose -f docker-compose-e2e.yml down
            docker system prune -a -f
            if [ "$exit_code" -eq "0" ]; then
              echo "Tests passed successfully!"
              exit 0
            else
              echo "Tests failed!"
              exit 1
            fi

#'👋 There is the last [Allure report](http://allure.rococo.website/allure-docker-service/projects/rococo/reports/latest/index.html)\n🕓 All reports [history](http://allureui.rococo.website/ui/allure-docker-service-ui/projects/rococo)'