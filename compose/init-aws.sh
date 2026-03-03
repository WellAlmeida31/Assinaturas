#!/bin/bash
set -e
echo "Await start localstack"
until awslocal secretsmanager list-secrets > /dev/null 2>&1; do
  sleep 2
done
awslocal secretsmanager create-secret --name sandbox/asaas/access_token --secret-string '{"token":"token_de_sandbox_asaas", "basico":"19.90", "premium":"39.90", "familia":"59.90"}'

echo "Create aws local resources"