#include "validator.h"
#include <sstream>
#include <assert.h>

void validator::validate(const medicament& m) {
	vector<string> msgs;
	if (m.getPrice() < 0) msgs.push_back("Pret negativ!!!");
	if (m.getName().size() == 0) msgs.push_back("Denumire vid!!!");
	if (m.getProducer().size() == 0) msgs.push_back("Producator vid!!!");
	if (m.getActSubst().size() == 0)msgs.push_back("Substanta activa vida!!!");
	if (msgs.size() > 0) {
		throw ValidateException(msgs);
	}
}

ostream& operator<<(ostream& out, const ValidateException& ex) {
	for (const auto& msg : ex.msgs) {
		out << msg << " ";
	}
	return out;
}

