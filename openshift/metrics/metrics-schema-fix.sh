#!/bin/bash

oc delete job hawkular-metrics-schema -n openshift-infra
oc create -n openshift-infra -f hawkular-metrics-schema.yml
