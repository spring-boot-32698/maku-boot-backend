# Debugging labs

Each file is broken on purpose. Apply it to the scratch namespace and diagnose it **before** reading the file:

    kubectl create namespace play            # once
    kubectl -n play apply -f <file>.yaml
    kubectl -n play get pods                 # symptom
    # ... diagnose with describe / logs / events ...
    kubectl -n play delete -f <file>.yaml    # clean up

Used by lessons 06 (probes & resources) and 11 (debugging playbook). Answers are in lesson 11.
