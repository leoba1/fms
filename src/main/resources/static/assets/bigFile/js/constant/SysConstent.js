var fileTypes = [{
	'logo': "./assets/bigFile/img/file-type/iso.png",
	'types': "iso,gho"
},{
	'logo': "./assets/bigFile/img/file-type/mv.png",
	'types': "rm,wav,mpg,rmvb,mov,mp4"
},{
	'logo': "./assets/bigFile/img/file-type/txt.png",
	'types': "txt,conf"
},{
	'logo': "./assets/bigFile/img/file-type/excel.png",
	'types': "xls,xlsx"
},{
	'logo': "./assets/bigFile/img/file-type/exe.png",
	'types': "exe,sh"
},{
	'logo': "./assets/bigFile/img/file-type/html.png",
	'types': "html,htm"
},{
	'logo': "./assets/bigFile/img/file-type/image.png",
	'types': "png,jpg,jpeg,bpm"
},{
	'logo': "./assets/bigFile/img/file-type/music.png",
	'types': "mp3,m4a"
},{
	'logo': "./assets/bigFile/img/file-type/pdf.png",
	'types': "pdf"
},{
	'logo': "./assets/bigFile/img/file-type/psd.png",
	'types': "psd"
},{
	'logo': "./assets/bigFile/img/file-type/sql.png",
	'types': "sql"
},{
	'logo': "./assets/bigFile/img/file-type/word.png",
	'types': "doc,docx"
},{
	'logo': "./assets/bigFile/img/file-type/ppt.png",
	'types': "ppt,pptx"
},{
	'logo': "./assets/bigFile/img/file-type/zip.png",
	'types': "zip,7z,gz"
},{
	'logo': "./assets/bigFile/img/file-type/apk.png",
	'types': "apk"
}];

function getFileLogo(suffix) {
	for (let ft of fileTypes) {
		if(ft.types.indexOf(suffix) != -1){
			return ft.logo;
		}
	}
	return "./assets/bigFile/img/file-type/other.png"
}
