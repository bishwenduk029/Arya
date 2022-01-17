package rasa.serializers

import api.ISerializer
import org.yaml.snakeyaml.DumperOptions
import org.yaml.snakeyaml.Yaml
import java.io.File

class YamlGenerator: ISerializer<Map<String, List<Map<String, String>>>> {

    override fun serialize(intents: Map<String, List<Map<String, String>>>) {
        val dumperOptions = DumperOptions()
        dumperOptions.indent = 2
        dumperOptions.defaultFlowStyle = DumperOptions.FlowStyle.BLOCK
        val result = Yaml(dumperOptions).dump(intents)
        File("rasa/data/train.yaml").writeText(result)
    }
}