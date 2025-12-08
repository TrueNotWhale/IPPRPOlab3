using System;
using System.Collections.Generic;

namespace SportsLeague.Data.Models;

public partial class Season
{
    public int SeasonId { get; set; }

    public string Name { get; set; } = null!;

    public virtual ICollection<Match> Matches { get; set; } = new List<Match>();
}
