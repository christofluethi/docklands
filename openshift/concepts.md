# Concepts
* [back](README.md)

## Labels
### Container
Use the following container labels for openshift
```
LABEL io.k8s.description="Platform for building and running Ruby 2.2 applications" \
      io.k8s.display-name="Ruby 2.2" \
      io.openshift.expose-services="8080:http" \
      io.openshift.tags="builder,ruby,ruby22"
```

### External Links
* Kubernetes - Labels and Selectors - https://kubernetes.io/docs/concepts/overview/working-with-objects/lables
* Project Atomic - Container Best Practices: http://docs.projectatomic.io/container-best-practices/
* Project Atomic - Standard Container/Application Identifiers: https://github.com/projectatomic/ContainerApplicationGenericLabels
