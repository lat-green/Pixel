import {CHAT_URI, sfetch} from "./FetchUtil";


export async function uploadFile(file: File, name: string = file.name) {
    const formData = new FormData();
    formData.append("file", file);
    formData.append("type", file.type);
    formData.append("name", name);

    return sfetch(`${CHAT_URI}/upload`, {
        method: 'POST',
        body: formData,
    }).then(resp => resp.json() as unknown as {
        value: string;
    }).then(json => new URL(`${CHAT_URI}${json.value}`))
}
