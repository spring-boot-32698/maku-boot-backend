{{/* Chart name, overridable */}}
{{- define "maku.name" -}}
{{- default .Chart.Name .Values.nameOverride | trunc 63 | trimSuffix "-" }}
{{- end }}

{{/* Base name for every resource: <release> or <release>-<chart> */}}
{{- define "maku.fullname" -}}
{{- if .Values.fullnameOverride }}
{{- .Values.fullnameOverride | trunc 63 | trimSuffix "-" }}
{{- else }}
{{- $name := default .Chart.Name .Values.nameOverride }}
{{- if contains $name .Release.Name }}
{{- .Release.Name | trunc 63 | trimSuffix "-" }}
{{- else }}
{{- printf "%s-%s" .Release.Name $name | trunc 63 | trimSuffix "-" }}
{{- end }}
{{- end }}
{{- end }}

{{/* Labels on every object */}}
{{- define "maku.labels" -}}
helm.sh/chart: {{ printf "%s-%s" .Chart.Name .Chart.Version | replace "+" "_" }}
app.kubernetes.io/name: {{ include "maku.name" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
app.kubernetes.io/version: {{ .Chart.AppVersion | quote }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
{{- end }}

{{/* Selector labels for one component. Usage: include "maku.selectorLabels" (dict "ctx" . "component" "backend") */}}
{{- define "maku.selectorLabels" -}}
app.kubernetes.io/name: {{ include "maku.name" .ctx }}
app.kubernetes.io/instance: {{ .ctx.Release.Name }}
app.kubernetes.io/component: {{ .component }}
{{- end }}

{{/* Secret holding DB passwords */}}
{{- define "maku.dbSecretName" -}}
{{- default (printf "%s-db" (include "maku.fullname" .)) .Values.database.existingSecret }}
{{- end }}

{{/* MySQL host/port the backend connects to */}}
{{- define "maku.mysqlHost" -}}
{{- if .Values.mysql.enabled }}{{ include "maku.fullname" . }}-mysql{{ else }}{{ required "externalDatabase.host is required when mysql.enabled=false" .Values.externalDatabase.host }}{{ end }}
{{- end }}
{{- define "maku.mysqlPort" -}}
{{- if .Values.mysql.enabled }}3306{{ else }}{{ .Values.externalDatabase.port }}{{ end }}
{{- end }}

{{/* Redis host/port */}}
{{- define "maku.redisHost" -}}
{{- if .Values.redis.enabled }}{{ include "maku.fullname" . }}-redis{{ else }}{{ required "externalRedis.host is required when redis.enabled=false" .Values.externalRedis.host }}{{ end }}
{{- end }}
{{- define "maku.redisPort" -}}
{{- if .Values.redis.enabled }}6379{{ else }}{{ .Values.externalRedis.port }}{{ end }}
{{- end }}

{{/* Image reference: repository:tag, tag defaults to appVersion */}}
{{- define "maku.image" -}}
{{- printf "%s:%s" .image.repository (default .ctx.Chart.AppVersion .image.tag) }}
{{- end }}
